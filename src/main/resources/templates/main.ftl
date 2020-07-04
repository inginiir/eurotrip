<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form class="form-inline" method="get" action="main">
                <input type="text" class="form-control" name="filter" placeholder="Search by note"
                       value="${filter!""}"/>
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
                    <input type="text"
                           class="form-control ${(nameNoteError??||activationError??)?string('is-invalid', '')}"
                           value="<#if travelNote??>${travelNote.nameNote}</#if>" name="nameNote"
                           placeholder="Enter name of note"/>
                    <#if nameNoteError??>
                        <div class="invalid-feedback">
                            ${nameNoteError}
                        </div>
                    <#elseif activationError??>
                        <div class="invalid-feedback">
                            ${activationError}
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
                        <input type="file" name="file" class="custom-file-input form-control ${(fileError??)?string('is-invalid', '')}"
                               id="inputGroupFile01" aria-describedby="inputGroupFileAddon01"/>
                        <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                        <#if fileError??>
                            <div class="invalid-feedback">
                                ${fileError}
                            </div>
                        </#if>
                    </div>
                </div>

                <div class="form-group" ID="items">
                    <input class="form-control ${(cityError??||ticketError??)?string('is-invalid', '')}"
                           name="originCity"
                           list="citiesList" placeholder="Choose your city"/>
                    <datalist id="citiesList">
                        <#if cities??>
                        <#list cities as city>
                        <option value="<#if city??>${city.name}(${city.code}), ${city.countryCode}<#else></#if>"/><br></br>
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
                           placeholder="Choose date"/><br></br>
                    <div class="alert alert-primary" role="alert">
                        Enter cities, which you would like to visit
                    </div>
                    <input type="button" class="btn btn-primary mt-3" value="Add city" onClick="AddItem();" ID="add"/>
                </div>


                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>
    <#include "parts/notesList.ftl"/>
 
    <script type="text/javascript">
        var items = 0;

        function AddItem() {
            div = document.getElementById("items");
            button = document.getElementById("add");
            items++;
            newitem = "<input class='form-control mt-3${(cityError??)?string('is-invalid', '')}' list='citiesL' name='city' placeholder='Choose city " + items + "'/>";
            newitem += "<datalist id='citiesL'>";
            newitem += "<#if cities??>";
            newitem += "<#list cities as city>";
            newitem += "<option value='${city.name}(${city.code})&sbquo; ${city.countryCode}'/><br></br>";
            newitem += "</#list>";
            newitem += "</#if>";
            newitem += "</datalist>";
            newnode = document.createElement("span");
            newnode.innerHTML = newitem;
            div.insertBefore(newnode, button);
        }
    </script>
</@c.page>