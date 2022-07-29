package option

import (
	"fmt"
	"time"
)

type Server struct {
	port           string
	timeout        time.Duration
	maxConnections int
}

type ServerOptions func(*Server)

func WithPort(port string) ServerOptions {
	return func(s *Server) {
		s.port = port
	}
}

func WithTimeout(timeout time.Duration) ServerOptions {
	return func(s *Server) {
		s.timeout = timeout
	}
}

func WithMaxConnections(maxConnections int) ServerOptions {
	return func(s *Server) {
		s.maxConnections = maxConnections
	}
}

func NewServer(options ...ServerOptions) *Server {
	server := &Server{}

	for _, option := range options {
		option(server)
	}

	return server
}

func main() {
	myServer := NewServer(
		WithMaxConnections(500),
		WithPort(":8080"),
		WithTimeout(time.Second*30),
	)
	fmt.Println(myServer)
}
