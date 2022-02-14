USE PRODUCT;

CREATE TABLE Drink
(
    DrinkID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100),
    Price INT,
    RegMemberId INT
);

-- 테스트용 더미 데이터
INSERT INTO Drink(Name, Price, RegMemberId) Values('싼 아메리카노', 500, 1);
INSERT INTO Drink(Name, Price, RegMemberId) Values('아메리카노', 2000, 1);
INSERT INTO Drink(Name, Price, RegMemberId) Values('비싼 아메리카노', 10000, 1);