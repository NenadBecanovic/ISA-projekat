import { MyUser } from './my-user';

describe('MyUser', () => {
  it('should create an instance', () => {
    // @ts-ignore
    expect(new MyUser()).toBeTruthy();
  });
});
