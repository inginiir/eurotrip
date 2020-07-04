<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div class="card-columns">
        <#list tickets as ticket>
            <div class="card">
                <img src="https://img7.socratify.net/de28db2e06146f8ab2_600x375.jpg" class="card-img-top">
                <div class="card-body">
                    <h5 class="card-title">Fly to travel!</h5>
                    <p class="card-text">Flight from <b>${ticket.getCityOfDepart().name}</b> to
                    <b>${ticket.getCityArrive().name}</b></p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Price: ${ticket.price} rub</li>
                    <li class="list-group-item">Number of changes: ${ticket.numberOfChanges}</li>
                    <li class="list-group-item">Duration of flying: ${ticket.duration / 60} hours</li>
                    <li class="list-group-item">Distance between cities: ${ticket.distance} kilometres</li>
                </ul>
                <div class="card-footer text-muted">
                    Date depart: ${ticket.departureDate?date}  <#if ticket.actual>Data is actual<#else>Data is not actual</#if>
                </div>
            </div>
        <#else>
            No tickets
        </#list>
    </div>

</@c.page>