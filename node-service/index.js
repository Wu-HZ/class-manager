// index.js
const express = require('express');
const http = require('http');
const { Server } = require('socket.io');
const cors = require('cors');

const app = express();
app.use(cors());
const server = http.createServer(app);
const io = new Server(server, {
  cors: { origin: '*' }
});

io.on('connection', (socket) => {
  console.log('User connected:', socket.id);
  socket.on('message', (data) => {
    console.log(data);
    io.emit('message', data);
  });
});

server.listen(3001, () => {
  console.log('Socket server running at http://localhost:3001');
});
