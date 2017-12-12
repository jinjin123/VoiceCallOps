<#list data as item>
资源名称: ${item.name}
设备总数: ${item.data.totalcount}
 <#list item.data.detail as detailData>
所属地: ${detailData.region}
数量:${detailData.count}
 </#list>
 
</#list>
