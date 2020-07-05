<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
    <h5>Hello, <#if name !="unknown"> ${name}<#else>guest</#if></h5>
    <div class="opacity alert alert-primary" role="alert">
        Welcome to EuroTrip!<br></br>
        The service allows you to search for the cheapest flight tickets and make routes between cities.<br></br>
        <i class="fas fa-exclamation-triangle"></i>
        Disclaimer! The service uses data from the Aviasales cache. This means that the database stores tickets that
        are searched by users of Aviasales.
    </div>
    <#if name == "unknown">
        <div>
            <div class="opacity alert alert-primary" role="alert">Please, <a href="/login">login </a></div>
        </div>
    </#if>
</@c.page>

