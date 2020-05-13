<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as s>

<@c.page>
    Add new user
    ${travelNote!""}

    <@s.login "/registration"/>
</@c.page>