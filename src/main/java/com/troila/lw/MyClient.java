package com.troila.lw;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

//名稱參數建議使用你要調用的服務的名稱，不強制
//當你這裡使用了ribbonclient註解的時候，spring會自動認為它是ribbon的子類，會用它覆蓋ribbon父類的效果
//但是具體幹活兒的規則，是由第二個參數中對應的類名決定的
//當然，這裡是通過硬編碼的方式來配置的，還可以通過配置文件的方式來配置
//當時用配置文件的方式時候，為了不衝突，一定將這裡的@ribbonclient註解注釋掉。
//@RibbonClient(name = "ek-provider" ,configuration = MyConfig.class)
public class MyClient {

}
