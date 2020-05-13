<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as s>

<@c.page>
    Login page
    <@s.login "/login"/>
    <a href="/registration">Sing Up</a>
</@c.page>