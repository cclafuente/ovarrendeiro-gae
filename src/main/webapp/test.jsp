<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/styles/estilos.css"/>
</head>
<body>
<div id="fb-root"></div>
<script>
var locale = "${pageContext.request.locale.language}_${pageContext.request.locale.country}";

if (locale.length != 5){
	try {
		locale = window.navigator.language.split('-');
		locale = locale[0].toLowerCase() + '_' + locale[1].toUpperCase();
	} catch (e) {
		locale = 'en_US';
	}
}

var response_name = null;

var APPID = '170054533104952';
var APP_SECRET = '68aebd74a580d06ba056a0d938c42c40';
var ACCESS_TOKEN = 'AAADwrA3ShtQBAHOR4tmeZBbqh8BZBuB12QHH3dAdZBddz1a6X0RNZAHI1B3nCjbgZBMqkIW0GKbKuUtHwP0LZBVL0tEiFGsMa9xIIBZCQZABCIPFthcaxXgj';

window.fbAsyncInit = function() {
    FB.init({
      appId      : APPID, // App ID
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true,  // parse XFBML
      oauth		 : true,
      level		 : "debug"
    });

	// Additional initialization code here
    FB.Canvas.setAutoResize();
    
    /* /oauth/access_token */
    // alert(document.top.location.href);
    
    var fb_sig_user = "<c:out value="${param.fb_sig_user}" />";
    
    var fb_sig_canvas_user = "<c:out value="${param.fb_sig_canvas_user}" />";
    
    alert(' fb_sig_user ' + fb_sig_user + ' fb_canvas_user ' + fb_sig_canvas_user);
    
 };


function userData(access_token) {
  		FB.api('/me?access_token=' + access_token, function(response) {
  		 if (!response || response.error) {
    			alert('Error occured -> ' + response.error.message);
  			} else {
    			$('#user_response_name').html(response.name);
  			}
		});
};

(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/" + locale + "/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>

<div class="black_title_text">
	<div>HI, <span style="color: yellow; text-transform: uppercase;" id="user_response_name"> FRIEND </span> ARE YOU READY?</div>
</div>

<div class="black_title_text">
	<div>MOVE, SEARCH AND GO!</div>
</div>

</body>
</html>
<!-- VERSION1 -->