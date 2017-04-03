<%--
  Created by IntelliJ IDEA.
  User: 林志杰
  Date: 2017/2/27
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang=zh-CN>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!-- Bootstrap core CSS -->
    <link href="/searchengine/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/searchengine/static/css/result.css">
</head>
<body>
<header>
    <div class="first-row">
        <a id="logo"><img src="/searchengine/static/img/googlelogo_color_120x44dp.png"></a>
        <form class="" name="searchform" action="/searchengine/search">
            <div class="row">
                <div class="col-md-4">
                    <div class="input-group">
                        <input type="text" class="form-control" name="term" id="search-form" value="${term}">
                        <input name="page_no" type="hidden" value="1">
                        <span class="input-group-btn">
                            <input href="" type="submit" class="btn btn-default" value="搜索"></input>
                        </span>
                    </div>
                </div>
            </div>

        </form>
    </div>
    <div class="second-row">
        <!--<ul >
            <li>第一页</li>
            <li>第二页</li>
            <li>第三页</li>
            <li>第四页</li>
        </ul>-->
    </div>
</header>
<div class="result-container">
    <section class="search-time">
        <span>找到约 ${docmax} 条结果 （用时 ${searchtime} 秒）</span>
    </section>
    <c:if test="${!empty employList}">
        <c:forEach items="${employList}" var="employ">
            <section class="phrase">
                <h1><a type="" href="${employ.link}">${employ.title}-<span class="source">${employ.source}</span></a></h1>
                <div>
                    <div class="info">
                        <div class="link-wrapper">
                            <a type="" href="${employ.link}"><span class="link">${employ.link}</span></a></h1>
                        </div>
                        <div>
                            <span class="publishtime">${employ.publishtime}</span><span><b>${employ.company}</b></span><span>薪资：${employ.salary}</span>
                            <span>学历：${employ.education}</span><span>${employ.attribute}</span><span>经验：${employ.experience}</span>
                        </div>
                        <div>

                        </div>
                    </div>
                    <div class="desc">
                            ${fn:substring(employ.description,0,100)}
                    </div>
                </div>

            </section>
        </c:forEach>
    </c:if>
</div>
</body>
</html>

