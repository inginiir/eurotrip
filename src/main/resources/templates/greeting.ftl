<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
    <h5>Hello, <#if name !="unknown"> ${name}<#else> guest</#if></h5>
    <div><p>The application which helps you to find the optimal route between countries.
            It seeks the fastest and the cheapest route. You can plan your traveling easily now.
            EuroTrip can analyze routes of trains, buses, and planes</p></div>
</@c.page>