<#import "parts/common.ftl" as c>

<@c.page>
    <h1>Travel note editor</h1>
    <form action="/main/editNote/" method="post" enctype="multipart/form-data">
        <div class="form-group mt-3">

            <div class="form-group">
                <input type="text" class="form-control ${(nameNoteError??)?string('is-invalid', '')}"
                       value="<#if note??>${note.nameNote}</#if>" name="nameNote"
                       placeholder="Enter name of travel note"/>
                <#if nameNoteError??>
                    <div class="invalid-feedback">
                        ${nameNoteError}
                    </div>
                </#if>
            </div>

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

            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file"
                           class="custom-file-input form-control ${(fileError??)?string('is-invalid', '')}"
                           id="inputGroupFile01" aria-describedby="inputGroupFileAddon01"/>
                    <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    <#if fileError??>
                        <div class="invalid-feedback">
                            ${fileError}
                        </div>
                    </#if>
                </div>
            </div>

            <input type="hidden" value="${note.id}" name="noteId">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>

        </div>
    </form>
</@c.page>