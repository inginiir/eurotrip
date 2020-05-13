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
            <label><input type="date" name="travelDate"/> </label>
            <label><input type="text" name="note" placeholder="Enter some note"/></label>
            <label><input type="checkbox" name="isVisited" /></label>
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
            <b>${note.id}</b>
            <span>${note.countryDestination}</span>
            <i>${note.travelDate?date}</i>
            <b>${note.note}</b>
            <b>${note.isVisited()?c}</b>
            <strong>${note.authorName}</strong>
        </div>
    <#else>
        No messages
    </#list>
</@c.page>