# Flowable

## 流程部署

```pthon

# 读取 Excel 文件（确保有数据）
df04 = pd.read_excel("/Users/warriorg/Downloads/task/20250204.xlsx")
df05 = pd.read_excel("/Users/warriorg/Downloads/task/20250205.xlsx")
df06 = pd.read_excel("/Users/warriorg/Downloads/task/20250206.xlsx")

# 数据修正
df04.loc[df04['状态'] == '已完成', '状态'] = '已关闭'
df04.loc[df04['状态'] == '开发中', '状态'] = '处理中'
df04.loc[df04['状态'] == '待处理', '状态'] = '处理中'
df04.loc[df04['状态'] == '待发版', '状态'] = '处理中'
df04.loc[df04['状态'] == '测试中', '状态'] = '待验证'

df05.loc[df05['状态'] == '已完成', '状态'] = '已关闭'
df05.loc[df05['状态'] == '开发中', '状态'] = '处理中'
df05.loc[df05['状态'] == '待处理', '状态'] = '处理中'
df05.loc[df05['状态'] == '待发版', '状态'] = '处理中'
df05.loc[df05['状态'] == '测试中', '状态'] = '待验证'
df05 = df05.query('状态 != "已关闭"').copy()

df06.loc[df06['状态'] == '已完成', '状态'] = '已关闭'
df06.loc[df06['状态'] == '开发中', '状态'] = '处理中'
df06.loc[df06['状态'] == '待处理', '状态'] = '处理中'
df06.loc[df06['状态'] == '待发版', '状态'] = '处理中'
df06.loc[df06['状态'] == '测试中', '状态'] = '待验证'

# 新增数据
df04_05 = pd.concat([df04, df05]).drop_duplicates()
new_data = df06[~df06['事项ID'].isin(df04_05['事项ID'])]
print(new_data['事项ID'].count())
new_data.groupby("状态")['事项ID'].size()

# 历史数据
df = pd.concat([df04, df05, df06], ignore_index=True)
df_sorted = df.sort_values(by="更新时间") 
df_latest = df_sorted.drop_duplicates(subset=["事项ID"], keep="last")
print(df_latest['事项ID'].count())
df_latest.groupby("状态")['事项ID'].size()


```

