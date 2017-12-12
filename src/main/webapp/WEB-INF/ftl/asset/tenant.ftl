<#list data as item>
租户名称: ${item.tenant_name}
 <#list item.resources as resourcesData>
${resourcesData.name}:${resourcesData.value}
 </#list>
 
</#list>
