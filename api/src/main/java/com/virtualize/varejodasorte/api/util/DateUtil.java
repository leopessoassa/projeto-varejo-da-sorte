package com.virtualize.varejodasorte.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.IllegalFormatException;

import static java.lang.Integer.parseInt;

public final class DateUtil {
    public static final String PADRAO_BRASIL = "dd/MM/yyyy";
    private static final String VAZIO = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
    private static final String YYYYMM = "yyyyMM";
    private static final String YYYYMMDD = "yyyyMMdd";
    private static final SimpleDateFormat FORMAT_YYYYMMDD = new SimpleDateFormat(YYYYMMDD);
    private static final SimpleDateFormat FORMAT_YYYYMM = new SimpleDateFormat(YYYYMM);
    private static final DateTimeFormatter FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern(YYYYMMDD);

    private DateUtil() {
    }

    public static Date gerar(int ano, int mes, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.DAY_OF_MONTH, dia);
        return cal.getTime();
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return newDate(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String format(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static Date parse(String source, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(source);
    }

    /**
     * Retorna o maximo entre duas datas. Uma data nula é tratada como sendo menor
     * que uma não-nula
     * <p>
     * Retorna nulo caso as duas datas sejam nulas
     */
    public static Date max(Date d1, Date d2) {
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }

        return (posterior(d1, d2)) ? d1 : d2;
    }

    public static Date diaAnterior(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date diaSeguinte(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * Retorna o minimo entre duas datas. Uma data nula é tratada como sendo maior
     * que uma não-nula
     * <p>
     * Retorna nulo caso as duas datas sejam nulas
     */
    public static Date min(Date d1, Date d2) {
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }

        return (d1.before(d2)) ? d1 : d2;
    }

    /**
     * Facilita a criação de uma nova data.
     *
     * @param day   1 a 31.
     * @param month 1 a 12.
     * @param year  ano.
     * @return A data informada.
     */
    public static Date newDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return truncate(cal.getTime());
    }

    /**
     * Retorna o dia atual truncado para 00:00.
     *
     * @return A data atual
     */
    public static Date hoje() {
        return truncate(new Date());
    }

    /**
     * Retorna o dia atual truncado para 00:00.
     *
     * @return A data atual
     */
    public static LocalDate hojeLocalDate() {
        return LocalDate.now();
    }

    /**
     * Retorna o dia atual e o horário
     *
     * @return A data e horário atual
     */
    public static LocalDateTime hojeLocalDateTime() {
        return LocalDateTime.now();
    }

    public static Date truncate(Date date) {
        if (date == null) {
            return null;
        }

        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        ZonedDateTime truncatedZonedDateTime = zonedDateTime.truncatedTo(ChronoUnit.DAYS);
        Instant truncatedInstant = truncatedZonedDateTime.toInstant();
        return Date.from(truncatedInstant);
    }

    /**
     * Retorna se <code>d1</code> é posterior ou igual a <code>d2</code> <br>
     * <br>
     * OBS: A datas serão comparadas com informação de hora truncada para 0:00.
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean posteriorOuIgual(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        return !truncate(new Date(d1.getTime())).before(truncate(new Date(d2.getTime())));
    }

    /**
     * Retorna se <code>d1</code> é posterior ou igual a <code>d2</code> <br>
     * <br>
     * OBS: A datas serão comparadas com informação de hora truncada para 0:00.
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean posteriorOuIgual(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        return d1.isAfter(d2) || d1.isEqual(d2);
    }

    /**
     * Retorna se <code>d1</code> é anterior ou igual a <code>d2</code> <br>
     * <br>
     * OBS: A datas serão comparadas com informação de hora truncada para 0:00.
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean anteriorOuIgual(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return !truncate(new Date(d1.getTime())).after(truncate(new Date(d2.getTime())));
    }

    /**
     * Retorna se <code>d1</code> é posterior a <code>d2</code> <br>
     * <br>
     * OBS: A datas serão comparadas com informação de hora truncada para 0:00.
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean posterior(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return truncate(new Date(d1.getTime())).after(truncate(new Date(d2.getTime())));
    }

    public static boolean posterior(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.isAfter(d2);
    }

    /**
     * Retorna se <code>d1</code> é anterior a <code>d2</code>. <br>
     * <br>
     * OBS: A datas serão comparadas com informação de hora truncada para 0:00.
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean anterior(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return truncate(new Date(d1.getTime())).before(truncate(new Date(d2.getTime())));
    }

    public static boolean anterior(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.isBefore(d2);
    }

    /**
     * Verifica se <code>data</code> está dentro (inclusive) de <code>inicio</code>
     * e <code>fim</code>
     *
     * @param data
     * @param inicio
     * @param fim
     * @return
     */
    public static boolean entre(Date data, Date inicio, Date fim) {
        if (data == null || inicio == null || fim == null) {
            return false;
        }

        // Tratamento caso as datas sejam passadas invertidas
        Date i = min(inicio, fim);
        Date f = max(fim, inicio);

        return posteriorOuIgual(data, i) && anteriorOuIgual(data, f);
    }

    /**
     * Retorna o dia do mês de <code>dt</code> .
     *
     * @param dt Data de referência.
     * @return dia do mês (1-31)
     */
    public static int dia(Date dt) {
        Calendar c = new GregorianCalendar();
        c.setTime(dt);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Retorna o mês de <code>dt</code> .
     *
     * @param dt Data de referência.
     * @return mês (1-12)
     */
    public static int mes(Date dt) {
        Calendar c = new GregorianCalendar();
        c.setTime(dt);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * Retorna o ano de <code>dt</code> .
     *
     * @param dt Data de referência.
     * @return ano de <code>data</code> .
     */
    public static int ano(Date dt) {
        Calendar c = new GregorianCalendar();
        c.setTime(dt);
        return c.get(Calendar.YEAR);
    }

    public static int anoAtual() {
        return ano(new Date());
    }

    public static Date ultimoDiaDoMes(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * Obs.: Independente da posicao que esteja a menor data, colocará a menor como
     * inicial
     *
     * @param data1
     * @param data2
     * @return Quantidade de meses entre datas. Se nao for possivel fazer o calculo,
     * retornará 0.
     */
    public static int mesesEntreDatas(Date data1, Date data2) {
        if (data1 == null || data2 == null) {
            return 0;
        }
        int retorno = 0;

        Calendar dataInicio = Calendar.getInstance();
        Calendar dataFim = Calendar.getInstance();
        if (anterior(data1, data2)) {
            dataInicio.setTime(newDate(1, mes(data1), ano(data1)));
            dataFim.setTime(newDate(1, mes(data2), ano(data2)));
        } else {
            dataInicio.setTime(newDate(1, mes(data2), ano(data2)));
            dataFim.setTime(newDate(1, mes(data1), ano(data1)));
        }

        while (!dataInicio.after(dataFim)) {
            retorno++;
            dataInicio.add(Calendar.MONTH, 1);
        }
        return retorno;
    }

    public static long diasEntreDatas(LocalDate data1, LocalDate data2) {
        if (data1 == null || data2 == null) {
            return 0;
        }

        return ChronoUnit.DAYS.between(data1, data2);
    }

    /**
     * @param data
     * @param quantidadeDias - dias para serem adicionados na data
     * @return
     */
    public static Date incrementaDiasNaData(Date data, int quantidadeDias) {
        Calendar retorno = Calendar.getInstance();

        retorno.setTime(data);
        retorno.add(Calendar.DAY_OF_MONTH, quantidadeDias);

        return retorno.getTime();
    }

    /**
     * @param data
     * @param quantidadeMeses - meses para serem adicionados na data
     * @return
     */
    public static Date incrementaMesesNaData(Date data, int quantidadeMeses) {
        Calendar retorno = Calendar.getInstance();

        retorno.setTime(data);
        retorno.add(Calendar.MONTH, quantidadeMeses);

        return retorno.getTime();
    }

    /**
     * Retorna uma data formatada a partir de uma data numérica
     *
     * @param data numerico no formato AAAAMMDD
     * @return data string dd/mm/AAAA
     */
    public static String formataDataAAAAMMDD(Integer data) {

        StringBuilder dataTexto = new StringBuilder();

        try {
            return dataTexto.append(data.toString(), 6, 8).append("/").append(data.toString(), 4, 6)
                    .append("/").append(data.toString(), 0, 4).toString();

        } catch (Exception e) {
            return VAZIO;
        }
    }

    /**
     * Retorna uma competencia formatada
     *
     * @param competencia numerico no formato AAAAMM
     * @return competencia string mm/AAAA
     */
    public static String formataCompetenciaAAAAMM(Integer competencia) {

        StringBuilder dataTexto = new StringBuilder();

        try {
            return dataTexto.append(competencia.toString(), 4, 6).append("/")
                    .append(competencia.toString(), 0, 4).toString();
        } catch (Exception e) {
            return VAZIO;
        }

    }

    public static Integer adicionaUmMesCompetencia(Integer competencia) {

        int mes = Integer.parseInt(competencia.toString().substring(4, 6));
        int ano = Integer.parseInt(competencia.toString().substring(0, 4));

        if (mes == 12) {
            mes = 1;
            ano++;
        } else {
            mes++;
        }

        return Integer.parseInt(
                String.format("%04d", ano) + String.format("%02d", mes));
    }

    public static Date fromIntegerAnoMesDia(Integer data) {
        try {
            return FORMAT_YYYYMMDD.parse(VAZIO + data);
        } catch (ParseException e) {
            return null;
        }
    }

    public static LocalDate fromIntegerAnoMesDiaToLocalDate(Integer data) {
        if (data != null && data != 0) {
            return LocalDate.parse(VAZIO + data, FORMATTER_YYYYMMDD);
        } else {
            return null;
        }
    }

    public static Date fromIntegerAnoMes(Integer data) {
        try {
            return FORMAT_YYYYMM.parse(VAZIO + data);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Converte data e hora de um formato numérico para um tipo Date
     *
     * @param data Data a ser convertida
     * @param hora Hora a ser convertida. Caso seja passado null, é considerado 0
     * @return Data com hr:min:seg. Retorna null caso <code>data</code> ou
     * <code>hora</code> esteja num formato inválido ou
     * <code>data = null</code>
     */
    public static Date fromNumberToDate(Number data, Number hora) {
        if (data == null) {
            return null;
        }

        try {

            // obs: Faz um padding com zeros à esquerda até tam 6 ou 8 caracteres.
            String strHora = hora == null ? String.format("%06d", 0) : String.format("%06d", hora);
            String strData = String.format("%08d", data);

            int hr = parseInt(strHora.substring(0, 2));
            int min = parseInt(strHora.substring(2, 4));
            int seg = parseInt(strHora.substring(4, 6));

            int ano = parseInt(strData.substring(0, 4));
            int mes = parseInt(strData.substring(4, 6));
            int dia = parseInt(strData.substring(6, 8));

            Calendar calendar = new GregorianCalendar(ano, mes - 1, dia, hr, min, seg);

            return calendar.getTime();
        } catch (IllegalFormatException | NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static Integer fromLocalDateToInteger(LocalDate date) {
        try {
            return Integer.parseInt(date.format(FORMATTER_YYYYMMDD));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static LocalDateTime toLocalDateTime(Date data) {
        if (data == null) {
            return null;
        }
        return LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate parsePadraoBrasil(String source) {
        LocalDate data = null;
        try {
            data = parseToLocalDate(source, PADRAO_BRASIL);
        } catch (ParseException e) {
            LOGGER.error("Erro ao converter data.", e);
        }
        return data;
    }

    public static boolean isBetweenFechado(LocalDate dt, LocalDate dtFirst, LocalDate dtLast) {
        if (dt == null || dtFirst == null || dtLast == null) {
            return false;
        }

        return !dt.isBefore(dtFirst) && !dt.isAfter(dtLast);
    }

    public static LocalDate getPrimeiroDiaMes(LocalDate dt) {
        return dt.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getUltimoDiaMes(LocalDate dt) {
        return dt.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static int diffYears(final LocalDate begin, final LocalDate end) {
        return Period.between(begin, end).getYears();
    }

    public static long diffMonths(final LocalDate begin, final LocalDate end) {
        return ChronoUnit.MONTHS.between(begin, end);
    }

    public static boolean isAfterOrEquals(LocalDate current, LocalDate candidate) {
        return current.isAfter(candidate) || isEquals(current, candidate);
    }

    public static boolean isBeforeOrEquals(LocalDate current, LocalDate candidate) {
        return current.isBefore(candidate) || isEquals(current, candidate);
    }

    public static boolean isEquals(LocalDate dt1, LocalDate dt2) {
        if (dt1 == null && dt2 == null) {
            return true;
        }

        return dt1 != null && dt2 != null && dt1.isEqual(dt2);
    }

    public static boolean isValid8LocalDate(Integer possibleLocalDate) {
        try {
            LocalDate.parse(VAZIO + possibleLocalDate, FORMATTER_YYYYMMDD);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static LocalDate parseToLocalDate(String source, String format) throws ParseException {
        Instant instant = new SimpleDateFormat(format).parse(source).toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
