CREATE TABLE "user" (
  "id" SERIAL PRIMARY KEY,
  "full_name" varchar,
  "username" varchar,
  "password" varchar,
  "first_name" varchar,
  "last_name" varchar,
  "email_address" varchar,
  "phone_number" varchar,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "role" (
  "id" SERIAL PRIMARY KEY,
  "role" varchar
);

CREATE TABLE "user_role" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int,
  "role_id" int
);

CREATE TABLE "user_address" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int,
  "address_line_1" varchar,
  "address_line_2" varchar,
  "address_line_3" varchar,
  "address_line_4" varchar,
  "city" varchar,
  "state" varchar,
  "country" varchar,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "shipping_address" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int,
  "recipient_name" varchar,
  "address_line_1" varchar,
  "address_line_2" varchar,
  "address_line_3" varchar,
  "address_line_4" varchar,
  "city" varchar,
  "state" varchar,
  "country" varchar,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "cart" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "cart_item" (
  "id" SERIAL PRIMARY KEY,
  "product_id" int,
  "cart_id" int,
  "quantity" int,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "order" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int,
  "details" varchar,
  "payment_id" int,
  "order_status_code" int,
  "shipping_address_id" int,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "order_status_code" (
  "id" SERIAL PRIMARY KEY,
  "status" varchar
);

CREATE TABLE "order_item" (
  "id" SERIAL PRIMARY KEY,
  "order_id" int,
  "product_id" int,
  "quantity" int DEFAULT 1,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "payment" (
  "id" SERIAL PRIMARY KEY,
  "amount" decimal,
  "payment_type" varchar,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "product" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "price" decimal,
  "description" varchar,
  "image_url" varchar,
  "inventory_id" int,
  "category_id" int,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

CREATE TABLE "category" (
  "id" int PRIMARY KEY,
  "category_name" varchar
);

CREATE TABLE "inventory" (
  "id" SERIAL PRIMARY KEY,
  "product_id" int,
  "quantity" int,
  "created_at" timestamp DEFAULT (now()),
  "updated_at" timestamp
);

ALTER TABLE "user_role" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "user_role" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "user_address" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "shipping_address" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "cart" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "cart_item" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "cart_item" ADD FOREIGN KEY ("cart_id") REFERENCES "cart" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("payment_id") REFERENCES "payment" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("order_status_code") REFERENCES "order_status_code" ("id");

ALTER TABLE "order" ADD FOREIGN KEY ("shipping_address_id") REFERENCES "shipping_address" ("id");

ALTER TABLE "order_item" ADD FOREIGN KEY ("order_id") REFERENCES "order" ("id");

ALTER TABLE "order_item" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "product" ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "inventory" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");
