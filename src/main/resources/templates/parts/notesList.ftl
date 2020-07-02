<#include "security.ftl">
<div class="card-columns">
    <#list travelNotes as note>
    <div class="card my-3">
        <#if note.filename??>
            <img src="/img/${note.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <h5>${note.nameNote}</h5><br/>
            <i>${note.note}</i><br/>
            <a href="/ticket/${note.id}" class="btn btn-primary">Show tickets</a>
        </div>
        <div class="card-footer text-muted">
            <a href="/user-notes/${note.author.id}">${note.authorName}</a>
            <#if note.authorName=name ><a href="/main/editNote/${note.id}">Edit</a><#else></#if>
            <#if note.authorName=name || isAdmin ><a href="/main/${note.id}">Delete</a><#else></#if>
        </div>
    </div>
    <#else>
    No notes
</#list>