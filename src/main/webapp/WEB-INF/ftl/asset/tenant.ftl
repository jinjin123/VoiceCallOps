<#list data as item>
租户名称: ${item.tenant_name}
 <#list item.resources as resourcesData>
资源名称: ${resourcesData.name}
数量:${resourcesData.value}

 </#list>
</#list>
