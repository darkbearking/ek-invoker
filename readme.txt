定時抓取eureka服務列表以及負載均衡相關的內容
負載均衡相關內容包括：MyRule、MyConfig、MyClient

RoundRobinRule 				//平均輪詢規則
AvailabilityFilteringRule 	//服務器三次連接失敗的被忽略規則，並發數過高的服務器也會被忽略，相關配置間Git
WeightedResponseTimeRule 	//每個服務器的相應權重值規則
ZoneAvoidanceRule 			//按照區域為可用服務器分類，然後按照不同的區域來處理負載的規則
BestAvailableRule 			//忽略那些以備短路，而且並發較低的服務器規則
RandomRule 					//隨機選用服務器規則
RetryRule 					//重試規則，有時候和平均輪詢規則配合使用
	
NFLoadBalancePingClassName		//實現通過ping測試服務器是否存活
NFLoadBalanceClassName			//實現指定自定義負載均衡器
NIWSServerListClassName			//實現指定服務器列表
NIWSServerListFilterClassName	//實現服務器的攔截