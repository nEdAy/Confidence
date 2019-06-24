package controller

import (
	"github.com/gin-gonic/gin"
	"github.com/rs/zerolog/log"
	"net/http"
)

func renderJSONWithError(c *gin.Context, error string) {
	log.Error().Msg(error)
	c.JSON(http.StatusBadRequest, gin.H{"error": error})
	return
}
