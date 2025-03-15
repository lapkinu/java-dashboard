-- Удаление существующих таблиц (если нужно)
DROP TABLE IF EXISTS
    public.playing_with_neon,
    public.invoices,
    public.revenue,
    public.customers,
    public.users CASCADE;

-- Удаление последовательности
DROP SEQUENCE IF EXISTS public.playing_with_neon_id_seq;

-- Создание расширения для UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Создание таблиц
CREATE TABLE public.customers (
                                  id uuid PRIMARY KEY DEFAULT public.uuid_generate_v4(),
                                  name varchar(255) NOT NULL,
                                  email varchar(255) NOT NULL,
                                  image_url varchar(255) NOT NULL
);

CREATE TABLE public.invoices (
                                 id uuid PRIMARY KEY DEFAULT public.uuid_generate_v4(),
                                 customer_id uuid NOT NULL,
                                 amount integer NOT NULL,
                                 status varchar(255) NOT NULL,
                                 date date NOT NULL
);

CREATE TABLE public.revenue (
                                month varchar(4) PRIMARY KEY,
                                revenue integer NOT NULL
);

CREATE TABLE public.users (
                              id uuid PRIMARY KEY DEFAULT public.uuid_generate_v4(),
                              name varchar(255) NOT NULL,
                              email text UNIQUE NOT NULL,
                              password text NOT NULL,
                              account_non_expired boolean,
                              account_non_locked boolean,
                              credentials_non_expired boolean,
                              enabled boolean
);

-- Создание sequence и таблицы playing_with_neon
CREATE SEQUENCE public.playing_with_neon_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.playing_with_neon (
                                          id integer PRIMARY KEY DEFAULT nextval('public.playing_with_neon_id_seq'),
                                          name text NOT NULL,
                                          value real
);

-- Вставка данных в customers
INSERT INTO public.customers (id, name, email, image_url) VALUES
                                                              ('3958dc9e-712f-4377-85e9-fec4b6a6442a', 'Delba de Oliveira', 'delba@oliveira.com', '/customers/delba-de-oliveira.png'),
                                                              ('3958dc9e-742f-4377-85e9-fec4b6a6442a', 'Lee Robinson', 'lee@robinson.com', '/customers/lee-robinson.png'),
                                                              ('76d65c26-f784-44a2-ac19-586678f7c2f2', 'Michael Novotny', 'michael@novotny.com', '/customers/michael-novotny.png'),
                                                              ('cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 'Amy Burns', 'amy@burns.com', '/customers/amy-burns.png'),
                                                              ('13d07535-c59e-4157-a011-f8d2ef4e0cbb', 'Balazs Orban', 'balazs@orban.com', '/customers/balazs-orban.png'),
                                                              ('d6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 'Evil Rabbit', 'evil@rabbit.com', '/customers/evil-rabbit.png');

-- Вставка данных в invoices
INSERT INTO public.invoices (id, customer_id, amount, status, date) VALUES
                                                                        ('6b95eb52-828b-4293-a667-44cc160f66b6', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8945, 'PAID', '2023-06-03'),
                                                                        ('1c6f2fe3-161b-42ef-917f-1e76cc97772e', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 1000, 'PAID', '2022-06-05'),
                                                                        ('11c27327-56a4-45b1-8a46-de656d9d1d7e', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 15795, 'PENDING', '2022-12-06'),
                                                                        ('64a6c17c-8190-4e93-864c-bf84811e3a46', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 20348, 'PENDING', '2022-11-14'),
                                                                        ('601b05f7-2aae-4b73-9c60-78a0957f805e', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 3040, 'PAID', '2022-10-29'),
                                                                        ('fc40a20c-ad87-4bfb-95bb-2f71e46936f0', '76d65c26-f784-44a2-ac19-586678f7c2f2', 44800, 'PAID', '2023-09-10'),
                                                                        ('c394b1a6-d80a-4236-bcc4-7b89cf3fa487', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 34577, 'PENDING', '2023-08-05'),
                                                                        ('c22ad3dc-78b0-4dba-9f86-79a8c89d4e4f', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 54246, 'PENDING', '2023-07-16'),
                                                                        ('5ba34891-e138-4b82-822b-f613dbdc9b6d', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 666, 'PENDING', '2023-06-27'),
                                                                        ('2177a369-9f31-442d-a24d-1850844a235a', '76d65c26-f784-44a2-ac19-586678f7c2f2', 32545, 'PAID', '2023-06-09'),
                                                                        ('da75a2fc-9b7a-497b-9932-8fed13fcabd5', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 1250, 'PAID', '2023-06-17'),
                                                                        ('48ac8889-ffd3-4876-a1ee-b2a22451ea6c', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8546, 'PAID', '2023-06-07'),
                                                                        ('8465e36d-5e70-4d08-855b-31e8f18ad5d6', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 500, 'PAID', '2023-08-19'),
                                                                        ('e2d26775-7099-4f6a-9e4f-6f3ac6d8ac5c', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8945, 'PAID', '2023-06-03'),
                                                                        ('742e45ad-1906-48cd-8f4f-54d575646a71', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 15795, 'PENDING', '2022-12-06'),
                                                                        ('9400d753-a8f0-4bb6-9dcd-807d159619b6', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 20348, 'PENDING', '2022-11-14'),
                                                                        ('7c47a3da-3467-47e9-862e-415cfe4cc0c3', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 3040, 'PAID', '2022-10-29'),
                                                                        ('e71a06e0-696d-4657-8e58-7ec4c02f8a5b', '76d65c26-f784-44a2-ac19-586678f7c2f2', 44800, 'PAID', '2023-09-10'),
                                                                        ('8cd90343-8458-4595-a994-2d0dd4efac7d', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 34577, 'PENDING', '2023-08-05'),
                                                                        ('0ead6537-ea75-422f-9a62-3983142f2a2d', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 54246, 'PENDING', '2023-07-16'),
                                                                        ('f90372e1-abc8-41c4-8168-28f9781ac8bf', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 666, 'PENDING', '2023-06-27'),
                                                                        ('1d6f389b-295b-40cf-ac85-e15580c010ee', '76d65c26-f784-44a2-ac19-586678f7c2f2', 32545, 'PAID', '2023-06-09'),
                                                                        ('4603ee86-8756-48b5-af01-20ff4f465777', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 1250, 'PAID', '2023-06-17'),
                                                                        ('7beaab1f-960a-4cba-ab5e-5dfd26e0104b', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8546, 'PAID', '2023-06-07'),
                                                                        ('16f05c3d-c144-42cf-849f-22bf773e8f49', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 500, 'PAID', '2023-08-19'),
                                                                        ('71ba0e46-bd9b-438f-aa8e-5586eec32f84', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8945, 'PAID', '2023-06-03'),
                                                                        ('f469a46c-8ab3-4106-a39e-4b84afbca034', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 1000, 'PAID', '2022-06-05'),
                                                                        ('567f6951-3641-428d-8fda-65474e1cad48', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 15795, 'PENDING', '2022-12-06'),
                                                                        ('8457a005-782e-4536-b3c1-726d0cd0f603', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 20348, 'PENDING', '2022-11-14'),
                                                                        ('f5c10108-d35e-4d67-926e-f0ee77637094', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 3040, 'PAID', '2022-10-29'),
                                                                        ('9338f349-0807-4ce5-bf07-c76ce2532aa0', '76d65c26-f784-44a2-ac19-586678f7c2f2', 44800, 'PAID', '2023-09-10'),
                                                                        ('523266b8-acb5-4430-8743-639d78aaaa57', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 54246, 'PENDING', '2023-07-16'),
                                                                        ('ef7056c4-6eb6-43de-aec5-f41b517ee649', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 666, 'PENDING', '2023-06-27'),
                                                                        ('f04db73e-6e48-4aa6-8a8e-be05081015cb', '76d65c26-f784-44a2-ac19-586678f7c2f2', 32545, 'PAID', '2023-06-09'),
                                                                        ('3e64e911-28ce-4644-b339-b3061d43a109', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 1250, 'PAID', '2023-06-17'),
                                                                        ('20ab3020-f6c5-4bba-9da6-52b12837a5c0', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8546, 'PAID', '2023-06-07'),
                                                                        ('7902a90e-de4f-451d-a44d-f75c0a1379c7', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 500, 'PAID', '2023-08-19'),
                                                                        ('0d5dd795-95be-4996-bbfa-d6bcaf26f98a', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8945, 'PAID', '2023-06-03'),
                                                                        ('e3da51ea-f43f-461f-a418-5db3976c0638', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 34570, 'PENDING', '2023-08-05'),
                                                                        ('d83464ed-881a-413e-b756-5179b381b54f', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 19999, 'PENDING', '2022-06-05'),
                                                                        ('e86b49dd-7766-41f5-ad33-0fd8881d1adf', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 203, 'PENDING', '2022-11-14'),
                                                                        ('f93307ab-d7c4-456f-bb8e-0be14fddc6a4', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 30, 'PENDING', '2022-10-29'),
                                                                        ('1462fe24-7d4a-4abe-b1d0-dca6db9bcb85', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 448, 'PENDING', '2023-09-10'),
                                                                        ('78bb9519-af57-441e-9d7e-7a5f508cc32b', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 345, 'PAID', '2023-08-05'),
                                                                        ('44aaf8c3-5746-4ae9-b641-f12f3c6d3cdb', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 542, 'PENDING', '2023-07-16'),
                                                                        ('97a575a5-2e72-464e-9c26-a0b6169cef41', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 325, 'PENDING', '2023-06-09'),
                                                                        ('cd182ed7-ecf8-4a30-b185-1783519d5f10', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 12, 'PENDING', '2023-06-17'),
                                                                        ('afb48e2c-5821-442b-bf4a-e0d2508c2f47', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 8547, 'PAID', '2023-06-07'),
                                                                        ('6d4d3faf-1cdd-4d6c-af25-7a8d9a64936e', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 100000000, 'PAID', '2023-08-19'),
                                                                        ('c18126bb-6e54-4c69-a276-8621bbe5db67', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 60000000, 'PENDING', '2023-06-27'),
                                                                        ('3fbbf1a0-9f67-4b0d-98e8-185eb036de86', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 200000, 'PAID', '2025-01-26'),
                                                                        ('d0b01d33-f1d5-4fd7-a7ed-d6aa64c7a9fb', '13d07535-c59e-4157-a011-f8d2ef4e0cbb', 55500, 'PENDING', '2025-02-27'),
                                                                        ('b452d19f-4710-4546-8561-b269b38d7621', '3958dc9e-712f-4377-85e9-fec4b6a6442a', 100000, 'PENDING', '2025-02-27'),
                                                                        ('b267d322-4e14-4eb0-bb24-9bd2eb2b803f', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 100000, 'PENDING', '2025-02-27'),
                                                                        ('516f5d84-133b-4340-8e7a-7ea8e1580af2', '3958dc9e-742f-4377-85e9-fec4b6a6442a', 100000, 'PENDING', '2025-02-27'),
                                                                        ('e8554acf-6a65-4eb7-abfd-32de3ccb6b43', '76d65c26-f784-44a2-ac19-586678f7c2f2', 29999, 'PAID', '2025-03-04'),
                                                                        ('3fd42318-39c6-454f-8564-f39c77e001ad', '76d65c26-f784-44a2-ac19-586678f7c2f2', 12300, 'PENDING', '2025-03-07'),
                                                                        ('a542cdc2-aff2-4ef2-a774-b21c4dbfc1b0', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 88800, 'PENDING', '2025-03-08'),
                                                                        ('55beeb41-09b4-478c-b5e2-772c08bb1a99', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 777, 'PENDING', '2025-03-08'),
                                                                        ('d78fcb92-9522-441a-8f5f-730ded7132d8', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 666, 'PENDING', '2025-03-07'),
                                                                        ('cc3088b1-1de2-449b-93e8-241349786a60', 'cc27c14a-0acf-4f4a-a6c9-d45682c144b9', 111100, 'PAID', '2025-03-10'),
                                                                        ('54512e4f-5bec-4b0c-b188-306c67059bef', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 121200, 'PAID', '2025-03-10'),
                                                                        ('b25442ce-a3ee-42c5-b94e-af6960383119', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 999900, 'PAID', '2025-03-10'),
                                                                        ('6c4b96e2-afaf-48ec-b701-ca87f70842a7', 'd6e15727-9fe1-4961-8c5b-ea44a9bd81aa', 11230056, 'PAID', '2025-03-10'),
                                                                        ('ed3ba4de-86fc-4692-a732-edc594fe4cc1', '76d65c26-f784-44a2-ac19-586678f7c2f2', 12345600, 'PAID', '2025-03-12');

-- Вставка данных в revenue
INSERT INTO public.revenue (month, revenue) VALUES
                                                ('Jan', 2000),
                                                ('Feb', 1800),
                                                ('Mar', 2200),
                                                ('Apr', 2500),
                                                ('May', 2300),
                                                ('Jun', 3200),
                                                ('Jul', 3500),
                                                ('Aug', 3700),
                                                ('Sep', 2500),
                                                ('Oct', 2800),
                                                ('Nov', 3000),
                                                ('Dec', 4800);

-- Вставка данных в users
INSERT INTO public.users (id, name, email, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES
                                                                                                                                    ('410544b2-4001-4271-9855-fec4b6a6442a', 'User', 'user@nextmail.com', '$2b$10$5dYeMccVkM.3itFMaASFjuuKrLAIgO.cKApqg9J/HWlWBoqdn.GOS', true, true, true, true),
                                                                                                                                    ('d6fff4f4-4c37-413e-b8df-559488af3d96', 'TestUser1', 'test1@example.com', '$2a$10$Htre7JLuXtmwA8iulRNZjuuPz2iLCURWn3/QL/Ktpn4xuVmNNwav2', true, true, true, true),
                                                                                                                                    ('e4500d28-09fc-43c6-9be7-91d34a5f8574', 'UserTest3', 'test3@example.com', '$2a$10$SosGEYQmc62iee6okpv.7eE/6apDC/ZCnsbihJTfBbgzMTwskSYEi', true, true, true, true);

-- Вставка данных в playing_with_neon
INSERT INTO public.playing_with_neon (id, name, value) VALUES
                                                           (1, 'c4ca4238a0', 0.34194186),
                                                           (2, 'c81e728d9d', 0.35837975),
                                                           (3, 'eccbc87e4b', 0.29922944),
                                                           (4, 'a87ff679a2', 0.25098425),
                                                           (5, 'e4da3b7fbb', 0.45313376),
                                                           (6, '1679091c5a', 0.82398885),
                                                           (7, '8f14e45fce', 0.02607676),
                                                           (8, 'c9f0f895fb', 0.7304265),
                                                           (9, '45c48cce2e', 0.36407447),
                                                           (10, 'd3d9446802', 0.33763814);

-- Обновление последовательности
SELECT setval('public.playing_with_neon_id_seq', 10);