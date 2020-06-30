<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div><h1>In developing...</h1>
        <p>Disclaimer! The service uses data from the Aviasales cache. This means that the database stores tickets that
            are searched by users of Aviasales. In the future, it is planned to switch to API Yandex.raspisaniya and add
            a
            search for tickets for trains, buses and trains. Also will be added search for tickets in real time</p>
    </div>
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

                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label " for="customFile">Choose file</label>
                    </div>
                </div>


                <div class="form-group" ID="items">
                    <input class="form-control ${(cityError??||ticketError??)?string('is-invalid', '')}"
                           name="originCity"
                           list="citiesList" placeholder="Enter your city">
                    <datalist id="citiesList">
                        <#if cities??>
                        <#list cities as city>
                        <option value="<#if city??>${city.name}/${city.countryCode}<#else></#if>"><br>
                            </#list>
                            </#if>
                    </datalist>
                    <#if cityError??>
                        <div class="invalid-feedback">
                            ${cityError}
                        </div>
                    <#elseif ticketError??>
                        <div class="invalid-feedback">
                            ${ticketError}
                        </div>
                    </#if>
                    <input type="date" class="form-control mt-3"
                           value="2020-07-01" name="departureDate"
                           placeholder="Choose date"/><br>
                    <p>Enter cities, which you would like to visit</p>
                    <input type="button" class="btn btn-primary mt-3" value="Add city" onClick="AddItem();" ID="add">
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
                    <a href="/ticket/${note.id}" class="btn btn-primary">Show details</a>
                </div>
                <div class="card-footer text-muted">
                    ${note.authorName}
                    <#if note.authorName=name ><a href="/main/editNote/${note.id}">Edit</a><#else></#if>
                    <#if note.authorName=name || isAdmin ><a href="/main/${note.id}">Delete</a><#else></#if>
                </div>
            </div>
        <#else>
            No notes
        </#list>

    </div>
    <script type="text/javascript">
        var items = 0;

        function AddItem() {
            div = document.getElementById("items");
            button = document.getElementById("add");
            items++;
            newitem = "<input class=\"form-control mt-3${(cityError??)?string('is-invalid', '')}\" list=\"citiesL\" name=\"city\" placeholder=\"Enter city " + items + "\">";
            newitem += "<datalist id=\"citiesL\">";
            newitem += "<#if cities??>";
            newitem += "<#list cities as city>";
            newitem += "<option value=\"${city.name}/${city.countryCode}\"><br>";
            newitem += "</#list>";
            newitem += "</#if>";
            newitem += "</datalist>";
            newnode = document.createElement("span");
            newnode.innerHTML = newitem;
            div.insertBefore(newnode, button);
        }
    </script>
</@c.page>