<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as s>

<@c.page>
    <div class="mb-1"> Add new user </div>

    ${message!""}

    <@s.login "/registration" true/>
</@c.page>