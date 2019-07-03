package service

import (
	"Shepherd/pkg/redis"
	"Shepherd/util"
	"errors"
	"gopkg.in/resty.v1"
)

func GetRanking(cacheKey string, parameterMap map[string]interface{}) (string, error) {
	rankingData, err := redis.Get(cacheKey)
	if err != nil {
		rankingData, err := getRankingSource(parameterMap)
		if err != nil {
			return "", err
		} else {
			defer redis.Set(cacheKey, rankingData, 2*3600)
			return rankingData, nil
		}
	} else {
		return string(rankingData), nil
	}
}

func getRankingSource(parameterMap map[string]interface{}) (string, error) {
	rankingData, err := resty.R().
		SetQueryParams(util.SignParameterMap(parameterMap)).
		Get("https://openapi.dataoke.com/api/goods/get-ranking-list")
	if err != nil {
		return "", err
	} else if rankingData.IsSuccess() {
		return rankingData.String(), nil
	} else {
		return "", errors.New("大淘客API异常")
	}
}
