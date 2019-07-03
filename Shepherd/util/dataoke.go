package util

import (
	"Shepherd/config"
	"bytes"
	"crypto/md5"
	"fmt"
	"sort"
)

func SignParameterMap(parameterMap map[string]interface{}) map[string]string {
	parameterMap["appKey"] = config.Dataoke.AppKey
	parameterMap["version"] = config.Dataoke.Version
	var buffer bytes.Buffer
	sortedMap(parameterMap, func(key string, value interface{}) {
		buffer.WriteString(key)
		buffer.WriteString("=")
		buffer.WriteString(value.(string))
		buffer.WriteString("&")
	})
	buffer.WriteString("key=")
	buffer.WriteString(config.Dataoke.AppSecret)
	sign := fmt.Sprintf("%x", md5.Sum(buffer.Bytes()))
	parameterMap["sign"] = sign
	return parameterMap
}

func sortedMap(m map[string]interface{}, f func(k string, v interface{})) {
	var keys []string
	for k := range m {
		keys = append(keys, k)
	}
	sort.Strings(keys)
	for _, k := range keys {
		f(k, m[k])
	}
}
