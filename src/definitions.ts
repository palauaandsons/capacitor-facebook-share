export interface FacebookSharePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
