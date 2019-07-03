package main

import (
	"github.com/tidwall/gjson"
	"github.com/tidwall/sjson"
	"log"
	"strconv"
)

const json = `{"friends":[{"first":"Dale","last":"Murphy","age":44},{"first":"Roger","last":"Craig","age":68},{"first":"Jane","last":"Murphy","age":47}]}`

type TestResp struct {
	Code uint64
}

var a = 0

func xx(json string) {
	value, _ := sjson.Delete(json, "friends."+strconv.Itoa(a)+".age")
	a++
	if a <= 100 {
		xx(value)
	} else {
		log.Fatalf("%s", value)
	}
}

func main() {
	// 首先我们判断该json是否合法
	if !gjson.Valid(json) {
		log.Fatalf("%s", "invalid json")
	}
	xx(json)
	//val := []byte(json)
	//str := jsoniter.Get(val, "friends.*.first").ToString()
	//fmt.Println(str)
	//for _, v := range jsonMap {
	//	fmt.Printf("%T, %+v\n", v, v)
	//}
	//for i  {
	//
	//}
	//value, _ := sjson.Delete(`{"friends":["Andy","Carol"]}`, "friends.-1")

	// 直接解析为map
	//jsonMap := gjson.Parse(json).Map()
	//fmt.Printf("%+v\n", jsonMap)

	//// 获取Json中的age
	//age := gjson.Get(json, `age`).Int()
	//fmt.Printf("%T, %+v\n", age, age)
	//// 获取lastname
	//lastname := gjson.Get(json, `name.last`).String()
	//fmt.Printf("%T, %+v\n", lastname, lastname)
	// 获取children数组
	//for _, v := range gjson.Get(json, `friends`).Array() {
	//	fmt.Printf("%q ", v.String())
	//}
	//fmt.Println()
	// 获取第二个孩子
	//fmt.Printf("%q\n", gjson.Get(json, `children.1`).String())
	//fmt.Printf("%q\n", gjson.Get(json, `children|1`).String())
	//// 通配符获取第三个孩子
	//fmt.Printf("%q\n", gjson.Get(json, `child*.2`).String())
	//// 反转数组函数
	//fmt.Printf("%q\n", gjson.Get(json, `children|@reverse`).Array())
	// 自定义函数 - 全转大写
	//gjson.AddModifier("case", func(json, arg string) string {
	//	if arg == "upper" {
	//		return strings.ToUpper(json)
	//	}
	//	return json
	//})
	//fmt.Printf("%q ", json)
	//fmt.Println()
	//fmt.Printf("%+v\n", gjson.Get(json, `friends|@case:upper`).Array())
}
