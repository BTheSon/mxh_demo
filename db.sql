-- 1. Tạo Database mới
CREATE DATABASE IF NOT EXISTS social_network_db;
USE social_network_db;

-- 2. Tạo bảng users (Người dùng)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tạo bảng posts (Bài đăng)
CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    body TEXT,
    user_id INT,
    status VARCHAR(20) DEFAULT 'draft',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Thiết lập khóa ngoại liên kết với bảng users
    CONSTRAINT fk_post_user FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- 4. Tạo bảng follows (Theo dõi)
CREATE TABLE follows (
    following_user_id INT,
    followed_user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Khóa chính hỗn hợp: Đảm bảo không có cặp follow trùng lặp
    PRIMARY KEY (following_user_id, followed_user_id),
    -- Khóa ngoại 1: Người đi theo dõi
    CONSTRAINT fk_follower FOREIGN KEY (following_user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    -- Khóa ngoại 2: Người được theo dõi
    CONSTRAINT fk_followed FOREIGN KEY (followed_user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    -- Ràng buộc logic: Không cho phép tự theo dõi chính mình
    CONSTRAINT chk_not_self_follow CHECK (following_user_id <> followed_user_id)
);