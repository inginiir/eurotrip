<#import "parts/common.ftl" as c>

<@c.page>
    <h1>Travel note editor</h1>
    <form action="/main/editNote/" method="post">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control ${(nameNoteError??)?string('is-invalid', '')}"
                           value="<#if note??>${note.nameNote}</#if>" name="countryDestination"
                           placeholder="Enter country destination"/>
                    <#if nameNoteError??>
                        <div class="invalid-feedback">
                            ${nameNoteError}
                        </div>
                    </#if>
                </div>
<#--                <div class="form-group">-->
<#--                    <input type="date" class="form-control"-->
<#--                           value="${note.travelDate?date?iso_utc!""}" name="travelDate"-->
<#--                           placeholder="Choose date"/>-->
<#--                </div>-->
                <div class="form-group">
                    <input type="text" class="form-control ${(noteError??)?string('is-invalid', '')}"
                           value="<#if note??>${note.note}</#if>" name="note"
                           placeholder="Enter note"/>
                    <#if noteError??>
                        <div class="invalid-feedback">
                            ${noteError}
                        </div>
                    </#if>
                </div>
<#--                <div class="form-group">-->
<#--                    <input type="checkbox" class="form-control"-->
<#--                           value="true" name="isVisited" ${note.visited?string("checked", "")} />-->
<#--                </div>-->
                <input type="hidden" value="${note.id}" name="noteId">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </form>
</@c.page>