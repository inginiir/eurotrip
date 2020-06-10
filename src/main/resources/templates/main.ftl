<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div><h1>In developing...</h1></div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form class="form-inline" method="get" action="main">
                <input type="text" class="form-control" name="filter" placeholder="Search by note"
                       value="${filter!""}">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new travel note
    </a>
    <div class="collapse <#if travelNote??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control ${(nameNoteError??)?string('is-invalid', '')}"
                           value="<#if travelNote??>${travelNote.nameNote}</#if>" name="nameNote"
                           placeholder="Enter name of note"/>
                    <#if nameNoteError??>
                        <div class="invalid-feedback">
                            ${nameNoteError}
                        </div>
                    </#if>
                </div>
<#--                <div class="form-group">-->
<#--                    <input type="date" class="form-control"-->
<#--                           value="2020-01-01" name="travelDate"-->
<#--                           placeholder="Choose date"/>-->
<#--                </div>-->
                <div class="form-group">
                    <input type="text" class="form-control ${(noteError??)?string('is-invalid', '')}"
                           value="<#if travelNote??>${travelNote.note}</#if>" name="note"
                           placeholder="Enter note"/>
                    <#if noteError??>
                        <div class="invalid-feedback">
                            ${noteError}
                        </div>
                    </#if>
                </div>
<#--                <div class="form-group">-->
<#--                    <input type="checkbox" class="form-control"-->
<#--                           value="true" name="isVisited">-->
<#--                </div>-->
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile" >
                        <label class="custom-file-label " for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card-columns">
        <#list travelNotes as note>
            <div class="card my-3">
                <#if note.filename??>
                    <img src="/img/${note.filename}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <h5>${note.nameNote}</h5><br/>
                    <i>${note.note}</i><br/>
<#--                    <i><#if note.travelDate??>  ${note.travelDate?date} <#else > no date</#if></i><br/>-->
<#--                    <b><#if note.visited>Visited<#else>Not visited</#if></b><br/>-->
                    <a href="/ticket" class="btn btn-primary">Show details</a>
                </div>
                <div class="card-footer text-muted">
                    ${note.authorName}
                    <#if note.authorName=name ><a href="/main/editNote/${note.id}">Edit</a><#else></#if>
                    <#if note.authorName=name || isAdmin ><a href="/main/${note.id}">Delete</a><#else></#if>
                </div>
            </div>
        <#else>
            No messages
        </#list>
    </div>
</@c.page>