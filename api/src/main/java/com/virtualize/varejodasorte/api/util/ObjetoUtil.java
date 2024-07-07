package com.virtualize.varejodasorte.api.util;

import com.virtualize.varejodasorte.api.exception.BadRequestException;
import com.virtualize.varejodasorte.api.exception.NotFoundException;
import com.virtualize.varejodasorte.api.filter.NbFilter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public final class ObjetoUtil {
    private ObjetoUtil() {
    }

    public static Long parseCpfDv(Long cpf, Integer cpfDv) {
        if (ObjectUtils.allNotNull(cpf, cpfDv)) {
            return Long.valueOf(String.format("%d%02d", cpf, cpfDv));
        }

        return null;
    }

    public static Long parseNitDv(Long nit, Integer nitDv) {
        if (ObjectUtils.allNotNull(nit, nitDv)) {
            return Long.valueOf(String.format("%d%d", nit, nitDv));
        }

        return null;
    }

    public static Boolean integerToBoolean(Integer valor) {
        Boolean bool = BooleanUtils.toBooleanObject(valor);
        return BooleanUtils.toBooleanDefaultIfNull(bool, false);
    }

    public static String formatarValorMonetario(Double valor) {
        String valorFormatado = "";
        DecimalFormat formatter = new DecimalFormat("###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

        if (valor != null) {
            valorFormatado = formatter.format(valor);
        }
        return valorFormatado;
    }

    public static String formatarValor3CasasDecimais(Double valor) {
        String valorFormatado = "";
        DecimalFormat df = new DecimalFormat("##0.000", new DecimalFormatSymbols(new Locale("pt", "BR")));

        if (valor != null) {
            valorFormatado = df.format(valor);
        }
        return valorFormatado;
    }

    public static void checkFilter(NbFilter filter) {
        if (filter == null || filter.getNb() == null) {
            throw new BadRequestException("Filtro invalido");
        }
    }

    public static <T> List<T> getList(Optional<List<T>> list, String msg) {
        if (!list.isPresent()) {
            throw new NotFoundException(msg);
        }

        List<T> values = list.get();
        if (values.isEmpty()) {
            throw new NotFoundException(msg);
        }
        return values;
    }
}
