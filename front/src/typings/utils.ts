type ValueOf<T> = T[keyof T];

type PropsWithRequiredChildren<P = unknown> = P & {
  children: React.ReactNode;
};

export { ValueOf, PropsWithRequiredChildren };
