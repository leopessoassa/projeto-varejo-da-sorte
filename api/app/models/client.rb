class Client < ApplicationRecord
    #scope :by_cpf, -> (cpf) { where(cpf: cpf) if cpf.present? }
    scope :by_cpf, -> (cpf) { where(cpf: cpf) }
end
