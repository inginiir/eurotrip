<#import "parts/common.ftl" as c>

<@c.page>
    Travel note editor
    <form action="/main/editNote/" method="post">
        <input type="text" name="countryDestination" value="${note.countryDestination}">
        <input type="date" name="travelDate" value="${note.travelDate?date?iso_utc!""}">
        <input type="text" name="note" value="${note.note}">
        <input type="checkbox" name="isVisited" value="true" ${note.visited?string("checked", "")}/>

        <input type="hidden" value="${note.id}" name="noteId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</@c.page>