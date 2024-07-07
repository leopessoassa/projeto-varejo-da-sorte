class CreateClients < ActiveRecord::Migration[7.1]
  def change
    create_table :clients do |t|
      t.string :name, :limit => 255
      t.string :cpf, :limit => 15
      t.string :tel, :limit => 15
      t.string :email, :limit => 255
      t.string :email_confirm, :limit => 255
      t.date :birthday

      t.timestamps
    end
  end
end
