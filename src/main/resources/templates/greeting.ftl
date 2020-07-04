<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
    <h5>Hello, <#if name !="unknown"> ${name}<#else>guest</#if></h5>
    <div class="opacity alert alert-primary" role="alert">
        Welcome to EuroTrip!<br></br>
        <i class="fas fa-exclamation-triangle"></i>
        Disclaimer! The service uses data from the Aviasales cache. This means that the database stores tickets that
        are searched by users of Aviasales. In the future, it is planned to switch to API Yandex.raspisaniya and add
        a search for tickets for trains and buses. Also will be added search for tickets in real time
    </div>
    <#if name == "unknown">
        <div>
            <div class="opacity alert alert-primary" role="alert">Please, <a href="/login">login </a></div>
        </div>
    </#if>
</@c.page>