package util

import (
	"crypto/md5"
	"encoding/hex"
	"io"
	"io/ioutil"
	"os"
	"path/filepath"
	"strconv"
)

// GetRootDir 获取程序跟目录
func GetRootDir() string {
	// 文件不存在获取执行路径
	file, err := filepath.Abs(filepath.Dir(os.Args[0]))
	if err != nil {
		file = "."
	}
	return file
}

// WritePidToFile 写pid到文件
func WritePidToFile(filename string) error {
	return ioutil.WriteFile(GetRootDir()+"/var/"+filename+".pid", []byte(strconv.Itoa(os.Getpid())), 0666)
}

// RemovePidFile 删除pid文件
func RemovePidFile(filename string) error {
	return os.Remove(GetRootDir() + "/var/" + filename + ".pid")
}

// GetFileMd5 获取文件md5值
func GetFileMd5(picPath string) (string, error) {
	// 获取文件md5
	file, err := os.Open(picPath)
	if err != nil {
		return "", err
	}
	md5h := md5.New()
	_, err = io.Copy(md5h, file)
	if err != nil {
		return "", err
	}
	return hex.EncodeToString(md5h.Sum(nil)), nil
}
