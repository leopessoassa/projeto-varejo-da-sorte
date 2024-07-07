require "cpf_cnpj"

class Api::V1::ClientsController < ApplicationController
  before_action :set_client, only: %i[ show update destroy ]

  # GET /clients?cpf=111.111.111-11
  def index
    if params[:cpf].present?
      if CPF.valid?(params[:cpf])
        @clients = Client.by_cpf(params[:cpf])
        if (@clients.length == 0)
          @client = Client.new(client_params)
        end
        render json: @clients
      else
        render json: { message: "CPF inválido"}, status: :bad_request
      end
    else
      render json: { message: "CPF é um campo obrigatório"}, status: :bad_request
    end
  end

  # GET /clients/1
  def show
    Rails.logger.debug("show.params=#{params}")
    render json: @client
  end

  # POST /clients
  def create
    @client = Client.new(client_params)

    if @client.save
      render json: @client, status: :created, location: api_v1_client_path(@client)
    else
      render json: @client.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /clients/1
  def update
    if @client.update(client_params)
      render json: @client
    else
      render json: @client.errors, status: :unprocessable_entity
    end
  end

  # DELETE /clients/1
  def destroy
    @client.destroy!
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_client
      @client = Client.find(params[:id])
      #@client = Client.find(params[:cpf])
    end

    # Only allow a list of trusted parameters through.
    def client_params
      params.require(:client).permit(:name, :cpf, :tel, :email, :email_confirm, :birthday)
    end
end
