你好,${whoask} 帅哥,数据中心运行良好,其中
<#list data as item>
<#if item.name == "致命">
有致命告警${item.value}条,<#elseif item.name == "严重">严重告警${item.value}条,<#elseif item.name == "一般">一般告警${item.value}条,<#elseif item.name == "警告">警告告警${item.value}条
</#if></#list>
有什么能为你服务的吗?