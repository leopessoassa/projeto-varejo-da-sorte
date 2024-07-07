# This file should ensure the existence of records required to run the application in every environment (production,
# development, test). The code here should be idempotent so that it can be executed at any point in every environment.
# The data can then be loaded with the bin/rails db:seed command (or created alongside the database with db:setup).
#
# Example:
#
#   ["Action", "Comedy", "Drama", "Horror"].each do |genre_name|
#     MovieGenre.find_or_create_by!(name: genre_name)
#   end
require 'faker'
Faker::Config.locale = 'pt-BR'
10.times do
    email = Faker::Internet.unique.email
    Client.create(
        name: Faker::Name.unique.name,
        cpf: Faker::IdNumber.brazilian_citizen_number(formatted: true),
        tel: Faker::PhoneNumber.cell_phone,
        email: email,
        email_confirm: email,
        birthday: Faker::Date.birthday(min_age: 18, max_age: 65)
    )
end