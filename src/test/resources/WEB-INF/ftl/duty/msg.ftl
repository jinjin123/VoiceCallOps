值班计划：
<#list data as item>
${item.date?string["yyyy-MM-dd"]}：${item.day}(早) ${item.night}(晚)
</#list>

查看 <a href="#">值班计划</a>