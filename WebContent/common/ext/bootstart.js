(function() {
	function getQueryParam(name) {
		var regex = RegExp('[?&]' + name + '=([^&]*)');

		var match = regex.exec(location.search) || regex.exec(path);
		return match && decodeURIComponent(match[1]);
	}

	function hasOption(opt, queryString) {
		var s = queryString || location.search;
		var re = new RegExp('(?:^|[&?])' + opt + '(?:[=]([^&]*))?(?:$|[&])',
				'i');
		var m = re.exec(s);

		return m ? (m[1] === undefined || m[1] === '' ? true : m[1]) : false;
	}

	function getCookieValue(name) {
		var cookies = document.cookie.split('; '), i = cookies.length, cookie, value;

		while (i--) {
			cookie = cookies[i].split('=');
			if (cookie[0] === name) {
				value = cookie[1];
			}
		}

		return value;
	}

	var scriptEls = document.getElementsByTagName('script'), 
	localhostTests = [
			/^localhost$/,
			/\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(:\d{1,5})?\b/ // IP v4
	], 
	host = window.location.hostname, 
	isDevelopment = null, 
	queryString = window.location.search, 
	test, path, i, ln, scriptSrc, match, 
	path = scriptEls[scriptEls.length - 1].src, 
	rtl = getQueryParam('rtl'), 
	theme = getQueryParam('theme')|| 'neptune', 
	includeCSS = !hasOption('nocss', path), 
	neptune = (theme === 'neptune'), 
	repoDevMode = getCookieValue('ExtRepoDevMode'), 
	suffix = [], 
	neptunePath;
	
	for (i = 0, ln = scriptEls.length; i < ln; i++) {
		scriptSrc = scriptEls[i].src;
		match = scriptSrc.match(/bootstrap\.js$/);
		if (match) {
			path = scriptSrc.substring(0, scriptSrc.length - match[0].length);
			break;
		}
	}

	rtl = rtl && rtl.toString() === 'true'

	path = path.substring(0, path.lastIndexOf('/'));

	if (theme && theme !== 'classic') {
		suffix.push(theme);
	}
	if (rtl) {
		suffix.push('rtl');
	}

	suffix = (suffix.length) ? ('-' + suffix.join('-')) : '';

	if (includeCSS) {
		document.write('<link rel="stylesheet" type="text/css" href="' + path
				+ '/resources/css/ext-all' + suffix + '-debug.css"/>');
	}

	if (queryString.match('(\\?|&)debug') !== null) {
		isDevelopment = true;
	} else if (queryString.match('(\\?|&)nodebug') !== null) {
		isDevelopment = false;
	}

	if (isDevelopment === null) {
		for (i = 0, ln = localhostTests.length; i < ln; i++) {
			test = localhostTests[i];

			if (host.search(test) !== -1) {
				isDevelopment = true;
				break;
			}
		}
	}

	if (isDevelopment === null && window.location.protocol === 'file:') {
		isDevelopment = true;
	}

	document.write('<script type="text/javascript" charset="UTF-8" src="'
			+ path + '/ext-all' + (isDevelopment ? '-dev' : '')
			+ '.js"></script>');

	if (neptune) {
		neptunePath = (repoDevMode ? path + '/..' : path)
				+ '/packages/ext-theme-neptune/build/ext-theme-neptune'
				+ (repoDevMode ? '-dev' : '') + '.js';

		if (repoDevMode && window.ActiveXObject) {
			Ext = {
				_beforereadyhandler : function() {
					Ext.Loader.loadScript({
						url : neptunePath
					});
				}
			};
		} else {
			document.write('<script type="text/javascript" src="' + neptunePath
					+ '" defer></script>');
		}
	}

})();