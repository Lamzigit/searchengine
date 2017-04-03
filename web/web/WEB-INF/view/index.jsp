<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- saved from url=(0037)http://v3.bootcss.com/examples/cover/ -->
<html lang="zh-CN"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="icon" href="http://v3.bootcss.com/favicon.ico">

  <title>Cover Template for Bootstrap</title>

  <!-- Bootstrap core CSS -->
  <link href="/searchengine/static/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="/searchengine/static/css/cover.css" rel="stylesheet">


  <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
  <!--[if lt IE 9]><script src="/searchengine/search/static/js/ie8-responsive-file-warning.js"></script><![endif]-->
  <script src="/searchengine/static/js/ie-emulation-modes-warning.js.下载"></script>

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
</head>

<body>

<div class="site-wrapper">

  <div class="site-wrapper-inner">

    <div class="cover-container">

      <div class="masthead clearfix">
        <div class="inner">
          <h3 class="masthead-brand">商标</h3>
          <nav>
            <ul class="nav masthead-nav">
              <li class="active"><a href="">首页</a></li>
              <li><a href="">发现</a></li>
              <li><a href="">个人中心</a></li>
            </ul>
          </nav>
        </div>
      </div>

      <div class="inner cover">
        <h1 class="cover-heading">找工作</h1>
        <form name="searchform" action="/searchengine/search">
          <p class="lead">
            <input class="form-control" name="term" type="text" id="formGroupInputLarge" placeholder="输入公司名称或职位">
            <input name="page_no" type="hidden" value="1">
          </p>
          <p class="lead">
            <input href="" type="submit" class="btn btn-lg btn-default" value="搜索"></input>
          </p>
        </form>
      </div>

      <%--<div class="mastfoot">--%>
        <%--<div class="inner">--%>
          <%--<p>Cover template for <a href="http://getbootstrap.com/">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p>--%>
        <%--</div>--%>
      <%--</div>--%>

    </div>

  </div>

</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/searchengine/static/js/jquery.min.js.下载"></script>
<script>window.jQuery || document.write('<script src="/searchengine/search/static/js/vendor/jquery.min.js"><\/script>')</script>
<script src="/searchengine/static/js/bootstrap.min.js.下载"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/searchengine/static/js/ie10-viewport-bug-workaround.js.下载"></script>


</body></html>