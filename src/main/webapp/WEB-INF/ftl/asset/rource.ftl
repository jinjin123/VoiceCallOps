<#--<#list data as item>-->
<#--资源名称: ${item.name}-->
<#--设备总数: ${item.data.totalcount}-->
 <#--<#list item.data.detail as detailData>-->
<#--所属地: ${detailData.region}-->
<#--数量:${detailData.count}-->
 <#--</#list>-->
 <#---->
<#--</#list>-->
<#list data as item>
<#if item.name == "physical_server">
物理机: ${item.data.totalcount}台
    <#list item.data.detail as detailData>
    <#if detailData.region == "JQ">
    金桥数据中心: ${detailData.count}台
    <#else >
    浦口数据中心: ${detailData.count}台
    </#if>
    </#list>
<#elseif item.name == "vm_server">
虚拟机: ${item.data.totalcount}台
<#else>
网络设备: ${item.data.totalcount}台
</#if>
</#list>
