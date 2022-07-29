package builder

import (
	"fmt"
	"time"
)

type Server struct {
	port           string
	timeout        time.Duration
	maxConnections int
}

func (s *Server) WithPort(port string) *Server {
	s.port = port
	return s
}
func (s *Server) WithTimeout(timeout time.Duration) *Server {
	s.timeout = timeout
	return s
}
func (s *Server) WithMaxConnections(maxConnections int) *Server {
	s.maxConnections = maxConnections
	return s
}

func NewServer() *Server {
	return &Server{}
}

func main() {
	server := NewServer().
		WithMaxConnections(500).
		WithTimeout(time.Second * 30).
		WithPort(":8080")

	fmt.Println(server)
}
