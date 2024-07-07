namespace NumberUtil {
  export function parseNumber(
    val: string | number | undefined,
    fallback?: number
  ): number | undefined {
    if (typeof val === "number") {
      return val;
    }
    if (typeof val === "undefined") {
      return fallback;
    }
    return Number(val);
  }

  export function onlyDigit(
    val: string | undefined,
    fallback?: number
  ): number | undefined {
    if (typeof val === "undefined") {
      return fallback;
    }
    return Number(val.replace(/[^\d]+/g, ""));
  }
}

export default NumberUtil;
