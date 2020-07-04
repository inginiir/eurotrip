<#include "security.ftl">
<#import "pager.ftl" as p>

<@p.pager url, page />
<div class="card-columns" id="note-list">
    <#list page.content as note>
        <div class="card my-3" data-id="${note.id}">
            <#if note.filename??>
                <img src="/img/${note.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <h5>${note.nameNote}</h5><br></br>
                <i>${note.note}</i><br></br>
            </div>
            <div class="card-footer text-muted continer">
                <div class="row">
                    <a class="col align-self-center" href="/user-notes/${note.author.id}">
                        ${note.authorName}
                    </a>
                    <a href="/ticket/${note.id}" class="col align-self-center" title="Show tickets">
                        <i class="fas fa-eye"></i>
                    </a>
                    <#if note.authorName=name >
                        <a class="col align-self-center" href="/main/editNote/${note.id}" title="Edit note">
                            <i class="fas fa-edit"></i>
                        </a>
                    <#else>
                    </#if>
                    <#if note.authorName=name || isAdmin >
                        <a class="col align-self-center" href="/main/${note.id}" title="Delete note">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                    <#else>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        No notes
    </#list>
</div>
<@p.pager url page />