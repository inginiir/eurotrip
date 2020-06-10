<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
<#list tickets as ticket>
    <div>
        <div>
            <h5>${ticket.price}</h5><br/>
            <i>${ticket.airline}</i><br/>
            <i><#if ticket.departureDate??>  ${ticket.departureDate?date} <#else > no date</#if></i><br/>
            <b>${ticket.flightNumber}</b><br/>
        </div>
        <div>

        </div>
    </div>
<#else>
    No tickets
</#list>
</@c.page>