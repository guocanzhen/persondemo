**使用H2数据库来模拟进行单元测试**

1、在写DAO层的单元测试时，我们往往会遇到一个问题，测试用例所依赖的数据库数据被修改或删除了，或者在一个新的环境下所依赖的数据库不存在，导致单元测试无法通过，进而构建失败。
在这种情况下，使用H2内存数据库来模拟数据库环境是一个很好的解决方案。官网链接如下：http://www.h2database.com/html/main.html。目前我研究的是模拟MySQL环境，对于各个数据库的兼容性可以查看如下链接：http://www.h2database.com/html/features.html#compatibility。




