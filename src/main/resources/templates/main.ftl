<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as s>

<@c.page>
    <div>
        <@s.logout />
        <span> <a href="/user"> User list </a> </span>
    </div>
    <div><h1>In developing...</h1></div>
    <div>
        <form method="post">
            <label><input type="text" name="countryDestination" placeholder="Enter destination country"/></label>
            <label><input type="date" name="travelDate" value="2020-01-01"/> </label>
            <label><input type="text" name="note" placeholder="Enter some note"/></label>
            <label><input type="checkbox" name="isVisited" value="true"></label>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add</button>
        </form>
    </div>
    <div>List of message
    <form method="get" action="main">
        <input type="text" name="filter" value="${filter!""}">
        <button type="submit">Find</button>
    </form>
    <#list travelNotes as note>
        <div>
            <form method="post" action="main">
<#--                <#if note.authorName=user.username ><a href="/main/${note.id}">x</a><#else>x</#if>-->
                <a href="/main/${note.id}">x</a>
            </form>
            <span>${note.countryDestination}</span>
            <i><#if note.travelDate??>  ${note.travelDate?date} <#else > no date</#if></i>
            <b>${note.note}</b>
            <b><input type="checkbox" name="isVisited" <#if note.visited>checked<#else></#if>></b>
            <strong>${note.authorName}</strong>
            <a href="/main/editNote/${note.id}">Edit</a>
<#--            <#if note.authorName=user.username ><a href="/main/edit/${note.id}">Edit</a><#else>x</#if>-->
        </div>
    <#else>
        No messages
    </#list>
</@c.page>