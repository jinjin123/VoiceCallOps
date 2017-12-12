<#list data as item>
<#if item.name == "致命">
当前有致命告警${item.value}条,<#elseif item.name == "严重">当前有严重告警${item.value}条,<#elseif item.name == "一般">当前有一般告警${item.value}条,<#elseif item.name == "警告">有警告告警${item.value}条
</#if></#list>
查看 <a href="http://112.65.23.133:7654/wwxweb/#/AlarmList">告警列表</a>